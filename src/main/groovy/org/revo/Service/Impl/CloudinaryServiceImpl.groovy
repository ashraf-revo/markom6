package org.revo.Service.Impl

import com.cloudinary.Cloudinary
import com.cloudinary.Transformation
import com.cloudinary.utils.ObjectUtils
import org.revo.Service.CloudinaryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by ashraf on 09/10/16.
 */
@Service
class CloudinaryServiceImpl implements CloudinaryService {
    @Autowired
    Cloudinary cloudinary

    @Override
    String UploadUserImage(String file) {
        Map map = cloudinary.uploader().uploadLarge(file.split(",")[1].decodeBase64(), ObjectUtils.asMap("transformation", new Transformation().crop("limit").width(250).height(250).crop("thumb").gravity("face")));
        map.get("secure_url") as String
    }

    @Override
    String Upload(String file) {
        Map map = cloudinary.uploader().uploadLarge(file.split(",")[1].decodeBase64(), ObjectUtils.emptyMap());
        map.get("secure_url") as String
    }

    @Override
    String Upload(byte[] file) {
        Map map = cloudinary.uploader().uploadLarge(file, ObjectUtils.emptyMap());
        map.get("secure_url") as String
    }
}