package com.bookshop.mybookshop.util;

import com.bookshop.mybookshop.aspect.logging.annotations.FileDestinationTraceable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;

@Service
public class UploadsUtils {

    @FileDestinationTraceable
    public void createDir(String uploadPath) throws IOException {
        Files.createDirectories(Paths.get(uploadPath));
    }
}
