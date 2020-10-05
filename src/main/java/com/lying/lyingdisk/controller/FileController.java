package com.lying.lyingdisk.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.lying.lyingdisk.common.convert.FileModelConvert;
import com.lying.lyingdisk.common.enums.ErrorCodeEnum;
import com.lying.lyingdisk.common.model.Result;
import com.lying.lyingdisk.common.model.file.*;
import com.lying.lyingdisk.entity.SysFile;
import com.lying.lyingdisk.entity.SysUser;
import com.lying.lyingdisk.service.FileDirService;
import com.lying.lyingdisk.service.FileService;
import com.lying.lyingdisk.service.UserService;
import com.lying.lyingdisk.util.FastDFSClientUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FastDFSClientUtil fastDFSClientUtil;
    @Autowired
    private FileDirService fileDirService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    /**
     * 文件上传
     */
    @PostMapping("/upload")
    @ResponseBody
    public Result uploadFile(MultipartFile file, UploadFileModel uploadFileModel) throws IOException {
        SysUser sysUser = userService.getByName(uploadFileModel.getUsername());
        uploadFileModel.setUid(sysUser.getUserId());

        //上传文件返回路径信息
        Map pathInfoMap = fastDFSClientUtil.uploadFile(file);

        //把已有的信息封装成我们的一个文件表的节点信息
        SysFile sysFile = convertFileInfoMap(file, pathInfoMap, uploadFileModel);

        //保存到文件表
        fileService.addFileInfo(sysFile);

        //重新查询新增后的页面信息，用于前台展示
        Result result = indexFilePage(uploadFileModel.getDirPid(), uploadFileModel.getUsername());

        return result;
    }

    /**
     * 文件下载
     */
    @GetMapping("/downloadFile")
    public void downloadFile(String id, HttpServletRequest request, HttpServletResponse response)  {
        InputStream inputStream=null;

        DownloadFileModel downloadFileModel= fileService.downloadFile(id);
        String fileName = downloadFileModel.getFileName();
        inputStream = downloadFileModel.getInputStream();
        String contentType = request.getServletContext().getMimeType(fileName);
        String contentDisposition = "attachment;filename=" + fileName;

        // 设置头
        response.setHeader("Content-Type",contentType);
        response.setHeader("Content-Disposition",contentDisposition);

        try {
            // 获取绑定了客户端的流
            ServletOutputStream output = response.getOutputStream();
            // 把输入流中的数据写入到输出流中
            IOUtils.copy(inputStream,output);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream !=null ){
                try {
                    inputStream.close();

                } catch (IOException e) {

                }

            }
        }


    }

    /**
     * 创建文件夹
     */
    @GetMapping("/createDir")
    @ResponseBody
    public Result createDir(@RequestParam String dirname,
                            @RequestParam Long pid,
                            @RequestParam String username) {
        SysUser sysUser = userService.getByName(username);
        Long uid = sysUser.getUserId();

        int i = fileDirService.createDir(dirname, pid, uid);
        if (i > 0) {
            List<AllFileModel> dirNames = fileDirService.queryDirNames(pid, uid);
            List<AllFileModel> fileNames = fileService.getFileNameByPid(pid, uid);
            if (CollectionUtil.isNotEmpty(dirNames)) {
                if (CollectionUtil.isNotEmpty(fileNames)) {
                    dirNames.addAll(fileNames);
                }
                return new Result(ErrorCodeEnum.SUCCESS.getCode(), "添加成功", "1", dirNames);
            } else {
                return new Result(ErrorCodeEnum.SUCCESS.getCode(), "添加成功", "1", fileNames);
            }
        } else {
            return new Result(ErrorCodeEnum.CREATEDIR_ERR.getCode(), ErrorCodeEnum.CREATEDIR_ERR.getDesc(), "1");
        }


    }

    /**
     * 查询文件或者文件夹信息
     */
    @GetMapping("/indexFilePage")
    @ResponseBody
    public Result indexFilePage(@RequestParam Long pid,
                                @RequestParam String username) {
        SysUser sysUser = userService.getByName(username);
        Long uid = sysUser.getUserId();

        List<AllFileModel> dirNames = fileDirService.queryDirNames(pid, uid);
        List<AllFileModel> fileNames = fileService.getFileNameByPid(pid, uid);
        if (CollectionUtil.isNotEmpty(dirNames)) {
            if (CollectionUtil.isNotEmpty(fileNames)) {
                dirNames.addAll(fileNames);
            }
            return new Result(ErrorCodeEnum.SUCCESS.getCode(), "添加成功", "1", dirNames);
        } else {
            return new Result(ErrorCodeEnum.SUCCESS.getCode(), "添加成功", "1", fileNames);
        }
    }

    /**
     * 删除文件，支持批量删除
     */
    @PostMapping("/deleteFile")
    @ResponseBody
    public Result deleteFile(@RequestBody String data){
        List<DeleteFileModel> models = JSON.parseArray(data, DeleteFileModel.class);
        List<String> dirIdList = models.stream()
                .map(model -> {
                    if (StrUtil.equals(model.getType(), "dir")) {
                        return model.getId();
                    }
                    return null;
                }).collect(Collectors.toList());

        List<String> fileIdList = models.stream()
                .map(model -> {
                    if (!StrUtil.equals(model.getType(), "dir")) {
                        return model.getId();
                    }
                    return null;
                }).collect(Collectors.toList());

        fileService.deleteFiles(fileIdList);
        fileDirService.deleteFileDir(dirIdList);
        return new Result(ErrorCodeEnum.SUCCESS.getCode(), "删除成功", "1");


    }



    public SysFile convertFileInfoMap(MultipartFile file,Map pathInfoMap,UploadFileModel uploadFileModel ){
        Map fileInfo = new HashMap();
        fileInfo.put("fileName",file.getOriginalFilename());
        fileInfo.put("fileSize",file.getSize());
        fileInfo.put("fileType",file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")+1));
        fileInfo.put("serverPath",pathInfoMap.get("serverPath"));
        fileInfo.put("nginxViewPath",pathInfoMap.get("nginxViewPath"));
        fileInfo.put("groupName",pathInfoMap.get("groupName"));
        fileInfo.put("uid",uploadFileModel.getUid());
        fileInfo.put("dirPid",uploadFileModel.getDirPid());
        SysFile sysFile = FileModelConvert.toEntity(fileInfo);
        return sysFile;
    }

}
