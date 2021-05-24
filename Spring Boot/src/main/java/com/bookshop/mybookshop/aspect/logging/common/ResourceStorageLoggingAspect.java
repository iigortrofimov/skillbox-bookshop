package com.bookshop.mybookshop.aspect.logging.common;

import java.nio.file.Path;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class ResourceStorageLoggingAspect {

    @After(value = "@annotation(com.bookshop.mybookshop.aspect.logging.annotations.FileDestinationTraceable)")
    public void trackUploadingDestination(JoinPoint joinPoint) {
        String uploadPath = (String) joinPoint.getArgs()[0];
        log.info("Created folder at: {}", uploadPath);
    }

    @AfterReturning(value = "@annotation(com.bookshop.mybookshop.aspect.logging.annotations.FileDestinationTraceable)", returning = "path")
    public void trackUploadingDestination(Path path) {
        log.info("book file path: {}", path);
    }

    @AfterReturning(value = "execution(public * com.bookshop.mybookshop.data.ResourceStorage.receiveMine(..))", returning = "mime")
    public void trackUploadingMediaType(MediaType mime) {
        log.info("book file mine type: {}", mime);
    }

    @AfterReturning(value = "execution(public * com.bookshop.mybookshop.data.ResourceStorage.receiveMine(..))", returning = "data")
    public void trackUploadingFileLength(byte[] data) {
        log.info("book file data len: {}", data.length);
    }
}
