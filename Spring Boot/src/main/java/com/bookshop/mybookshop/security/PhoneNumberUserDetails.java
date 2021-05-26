package com.bookshop.mybookshop.security;

public class PhoneNumberUserDetails extends BookStoreUserDetails {


    public PhoneNumberUserDetails(BookStoreUser bookStoreUser) {
        super(bookStoreUser);
    }

    @Override
    public String getUsername() {
        return getBookStoreUser().getPhone();
    }
}
