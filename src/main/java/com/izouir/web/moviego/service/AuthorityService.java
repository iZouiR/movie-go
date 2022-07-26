package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Authority;
import com.izouir.web.moviego.exception.AuthorityNotFoundException;

public interface AuthorityService {
    Authority findAuthority(String authority) throws AuthorityNotFoundException;
}
