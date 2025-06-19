package com.veterinary.core.config.exception;

public class ExceptionMessages {

    public static final String ALL_READY_EXISTS = "Veri zaten kayıtlı.";
    public static final String VACCINE_NOT_FOUND = "%s ID'li aşılama bulunamadı.";
    public static final String CUSTOMER_NOT_FOUND = "%s ID'li kullanıcı bulunamadı.";
    public static final String ANIMAL_NOT_FOUND = "%s ID'li hayvan bulunamadı.";
    public static final String CUSTOMER_ANIMALS_NOT_FOUND = "%s ID'li kullanıcıya ait hayvan bulunamadı.";
    public static final String EMAIL_EXISTS = "%s daha önceden kaydedilmiş.";
    public static final String PHONE_EXISTS = "%s numarası önceden kaydedilmiş.";
    public static final String DOCTOR_NOT_FOUND = "%s ID'li doktor bulunamadı.";
    public static final String DOCTOR_ALREADY_AVAILABLE_ON_DATE = "%s ID'li doktor %s tarihinde zaten müsait.";
    public static final String AVAILABLE_DATE_CANNOT_BE_IN_PAST = "Geçmiş tarih bilgisi giremezsiniz.";
    public static final String DATE_CANNOT_BE_IN_PAST = "Hatalı tarih bilgisi girildi.";
    public static final String VACCINE_ALL_READY_EXISTS = "Bu hayvana ait '%s' kodlu aşının geçerliliği %s tarihine kadar devam ediyor.";
    public static final String ANIMAL_VACCINES_NOT_FOUND = "%s ID'li hayvana ait aşı bilgisi bulunamadı.";
    public static final String DOCTOR_NOT_AVAILABLE = "%s ID'li doktor %s tarihinde çalışmıyor.";
    public static final String DOCTOR_NOT_AVAILABLE_THIS_HOUR = "%s ID'li doktorun %s saatinde başka bir randevusu mevcut. Başka tarih deneyin.";
    public static final String APPOINTMENT_NOT_FOUND = "%s ID'li randevu bulunamadı.";
    public static final String CUSTOMER_NAME_NOT_FOUND = "%s ismiyle alakalı kullanıcı bulunamadı.";
    public static final String AVAILABLE_DATE_NOT_FOUND = "%s ID'li müsaitlik verisi bulunamadı.";
    public static final String DOCTOR_APPOINTMENT_NOT_FOUND = "%s ID'li doktorun %s tarihinden %s tarihine kadar randevusu bulunamadı";
    public static final String ANIMAL_APPOINTMENT_NOT_FOUND = "%s ID'li hayvanın %s tarihinden %s tarihine kadar randevusu bulunamadı";
}
