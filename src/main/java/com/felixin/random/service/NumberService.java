package com.felixin.random.service;


import com.felixin.random.dto.*;

import java.util.List;

public interface NumberService {
    ResponseDTO generateRandomNumber();

    List<NumberDTO> getNumbers(Long i);
}