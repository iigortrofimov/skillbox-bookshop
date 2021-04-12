package com.bookshop.mybookshop.domain.book;

public enum BookFileType {
    PDF(".pdf"),
    EPUB(".epub"),
    FB2(".fb2");

    private final String fileExtension;

    BookFileType(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public static String getExtensionByTypeId(Integer typeId) {
        switch (typeId) {
            case 1:
                return BookFileType.PDF.fileExtension;
            case 2:
                return BookFileType.EPUB.fileExtension;
            case 3:
                return BookFileType.FB2.fileExtension;
            default:
                return "";
        }
    }
}
