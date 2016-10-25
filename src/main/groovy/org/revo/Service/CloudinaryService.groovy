package org.revo.Service

/**
 * Created by ashraf on 09/10/16.
 */
interface CloudinaryService {
    String Upload(String file)
    String UploadUserImage(String file)

    String Upload(byte[] file)
}