package com.veterinary.utilies;

import com.veterinary.core.result.Result;
import com.veterinary.core.result.ResultData;
import com.veterinary.dto.CursorResponse;
import org.springframework.data.domain.Page;


public class ResultHelper {

    public static <T> ResultData<T> created(T data){
        return new ResultData<>(true,"Kayıt başarılı","201",data);
    }

    public static <T> ResultData<T> success(T data){
        return new ResultData<>(true, "İşlem Başarılıaa","200",data);
    }


    public static Result notFoundError(String msg){
        return new Result(false, msg,"404");
    }
    public static Result badRequestError(String msg){
        return new Result(false, msg,"409");
    }
    public static <T> ResultData<CursorResponse<T>> cursor(Page<T> pageData){
        CursorResponse<T> cursor = new CursorResponse<>();
        cursor.setItems(pageData.getContent());
        cursor.setPageNumber(pageData.getNumber());
        cursor.setPageSize(pageData.getSize());
        cursor.setTotalElement(pageData.getTotalElements());

        return ResultHelper.success(cursor);
    }

    public static <T> ResultData<T> validationError(T data) {
        return new ResultData<>(false, "Error","400",data);
    }
}
