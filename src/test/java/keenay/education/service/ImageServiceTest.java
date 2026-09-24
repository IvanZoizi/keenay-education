package keenay.education.service;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import keenay.education.security.CustomUserDetail;
import keenay.education.service.image.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {

    private static final String BUCKET_NAME = "test-bucket";
    private static final Long USER_ID = 1L;

    @Mock
    private MinioClient minioClient;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private CustomUserDetail userDetail;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(imageService, "bucketName", BUCKET_NAME);
        lenient().when(userDetail.getUser().getId()).thenReturn(USER_ID);
    }

    @Test
    @DisplayName("Тест uploadPhoto бакет уже существует")
    public void testUploadPhotoBucketExists() throws Exception {
        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(true);
        when(multipartFile.getOriginalFilename()).thenReturn("photo.jpg");
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
        when(multipartFile.getSize()).thenReturn(0L);
        when(multipartFile.getContentType()).thenReturn("image/jpeg");

        String result = imageService.uploadPhoto(multipartFile, userDetail);

        assertNotNull(result);
        assertTrue(result.startsWith("users/" + USER_ID + "/photos/"));
        assertTrue(result.endsWith(".jpg"));
        verify(minioClient).bucketExists(any(BucketExistsArgs.class));
        verify(minioClient, never()).makeBucket(any(MakeBucketArgs.class));
        verify(minioClient).putObject(any(PutObjectArgs.class));
    }

    @Test
    @DisplayName("Тест uploadPhoto бакет не существует")
    public void testUploadPhotoBucketNotExists() throws Exception {
        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("photo.png");
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
        when(multipartFile.getSize()).thenReturn(0L);
        when(multipartFile.getContentType()).thenReturn("image/png");

        String result = imageService.uploadPhoto(multipartFile, userDetail);

        assertNotNull(result);
        assertTrue(result.endsWith(".png"));
        verify(minioClient).bucketExists(any(BucketExistsArgs.class));
        verify(minioClient).makeBucket(any(MakeBucketArgs.class));
        verify(minioClient).putObject(any(PutObjectArgs.class));
    }

    @Test
    @DisplayName("Тест uploadPhoto без расширения")
    public void testUploadPhotoWithoutExtension() throws Exception {
        when(minioClient.bucketExists(any(BucketExistsArgs.class))).thenReturn(true);
        when(multipartFile.getOriginalFilename()).thenReturn("photo");
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
        when(multipartFile.getSize()).thenReturn(0L);
        when(multipartFile.getContentType()).thenReturn("application/octet-stream");

        String result = imageService.uploadPhoto(multipartFile, userDetail);

        assertNotNull(result);
        assertTrue(result.startsWith("users/" + USER_ID + "/photos/"));
        assertFalse(result.contains("."));
        verify(minioClient).putObject(any(PutObjectArgs.class));
    }

    @Test
    @DisplayName("Тест uploadPhoto ошибка Minio")
    public void testUploadPhotoThrowsException() throws Exception {
        when(minioClient.bucketExists(any(BucketExistsArgs.class)))
                .thenThrow(new RuntimeException("Minio error"));

        String result = imageService.uploadPhoto(multipartFile, userDetail);

        assertNull(result);
        verify(minioClient).bucketExists(any(BucketExistsArgs.class));
        verify(minioClient, never()).putObject(any(PutObjectArgs.class));
    }

    @Test
    @DisplayName("Тест getUrlForPhoto")
    public void testGetUrlForPhotoSuccess() throws Exception {
        String photoName = "users/1/photos/abc.jpg";
        String expectedUrl = "http://minio/presigned-url";

        when(minioClient.getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class)))
                .thenReturn(expectedUrl);

        String result = imageService.getUrlForPhoto(photoName);

        assertNotNull(result);
        assertEquals(expectedUrl, result);
        verify(minioClient).getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class));
    }

    @Test
    @DisplayName("Тест getUrlForPhoto MinioException")
    public void testGetUrlForPhotoMinioException() throws Exception {
        when(minioClient.getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class)))
                .thenThrow(mock(MinioException.class));

        String result = imageService.getUrlForPhoto("photo.jpg");

        assertNull(result);
        verify(minioClient).getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class));
    }

    @Test
    @DisplayName("Тест getUrlForPhoto общая ошибка")
    public void testGetUrlForPhotoGenericException() throws Exception {
        when(minioClient.getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        String result = imageService.getUrlForPhoto("photo.jpg");

        assertNull(result);
        verify(minioClient).getPresignedObjectUrl(any(GetPresignedObjectUrlArgs.class));
    }
}