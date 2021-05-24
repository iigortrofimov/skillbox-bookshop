package com.bookshop.mybookshop.data;

import com.bookshop.mybookshop.aspect.logging.annotations.FileDestinationTraceable;
import com.bookshop.mybookshop.dao.BookFileRepository;
import com.bookshop.mybookshop.domain.book.BookFile;
import com.bookshop.mybookshop.util.UploadsUtils;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ResourceStorage {

    /**
     * Path to upload.
     */
    @Value("${upload.path}")
    String uploadPath;

    /**
     * Download path.
     */
    @Value("${download.path}")
    String downloadPath;

    /**
     * BookFile repository.
     */
    private final BookFileRepository bookFileRepository;

    /**
     * UploadsUtils.
     */
    private final UploadsUtils uploadsUtils;

    /**
     * Saves book file at {@code this.uploadPath} and returns entire resource URI.
     *
     * @param file book file.
     * @param slug mnemonical identifier.
     * @return full resource URI.
     * @throws IOException IO exception.
     */
    public String saveNewBookImage(MultipartFile file, String slug) throws IOException {
        String resourceURI = null;
        if (!file.isEmpty()) {
            if (!new File(uploadPath).exists()) {
                uploadsUtils.createDir(uploadPath);
            }
            String fileName = slug + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            Path path = Paths.get(uploadPath, fileName);
            file.transferTo(path);
            resourceURI = "/book-images/" + fileName;
        }
        return resourceURI;
    }

    /**
     * Returns {@link Path} of book file by hash from repo.
     *
     * @param hash book file hash.
     * @return {@code Path} of book file.
     */
    @FileDestinationTraceable
    public Path receiveBookFilePath(String hash) {
        return Paths.get(bookFileRepository.findBookFileByHash(hash).getPath());
    }

    /**
     * Returns {@link MediaType} - book file mine type by hash from repo.
     *
     * @param hash book file hash.
     * @return {@link MediaType} - book file mine type.
     */
    public MediaType receiveMine(String hash) {
        BookFile book = bookFileRepository.findBookFileByHash(hash);
        String mineType = URLConnection.guessContentTypeFromName(Paths.get(book.getPath()).getFileName().toString());
        if (mineType != null) {
            return MediaType.parseMediaType(mineType);
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }

    /**
     * Return {@code byte[]} of book file by hash from repo.
     *
     * @param hash book file hash.
     * @return book file {@code byte[]}.
     * @throws IOException exception.
     */
    public byte[] receiveBookFileBytes(String hash) throws IOException {
        BookFile book = bookFileRepository.findBookFileByHash(hash);
        Path path = Paths.get(downloadPath, book.getPath());
        return Files.readAllBytes(path);
    }
}
