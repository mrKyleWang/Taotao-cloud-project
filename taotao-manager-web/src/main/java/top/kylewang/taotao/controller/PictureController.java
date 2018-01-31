package top.kylewang.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.kylewang.taotao.common.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 图片上传controller
 *
 * @author Kyle.Wang
 * 2018/1/30 0030 21:16
 */

@Controller
public class PictureController {

    @Value("${IMAGE_PATH}")
    private String IMAGE_PATH;

    private final ResourceLoader resourceLoader;

    @Autowired
    public PictureController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * 图片上传
     *
     * @param uploadFile
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
    @ResponseBody
    public String upload(MultipartFile uploadFile, HttpServletRequest request) throws IOException {
        Map<String, Object> result = new HashMap<>(4);
        try {
            String saveUrl = request.getContextPath() + "/" + IMAGE_PATH;
            System.out.println(saveUrl);
            // 生成随机图片名
            UUID uuid = UUID.randomUUID();
            // 文件扩展名
            String ext = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf("."));
            // 保存图片名
            String saveFileName = uuid + ext;
            // 保存图片
            File absoluteFile = new File(IMAGE_PATH + "/" + saveFileName).getAbsoluteFile();
            uploadFile.transferTo(absoluteFile);
            // 向浏览器响应响应数据
            result.put("error", 0);
            result.put("url", saveUrl + "/" + saveFileName);
            return JsonUtils.objectToJson(result);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", 1);
            result.put("message", "图片上传失败");
            return JsonUtils.objectToJson(result);
        }

    }

    /**
     * 图片显示
     *
     * @param filename
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/upload/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {

        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get("upload", filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
