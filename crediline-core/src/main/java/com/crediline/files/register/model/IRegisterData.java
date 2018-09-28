package com.crediline.files.register.model;

import java.text.SimpleDateFormat;
import java.util.Locale;

public interface IRegisterData {
	public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH);
	public static SimpleDateFormat FILENAME_DATE_FORMAT = new SimpleDateFormat("yyMMdd", Locale.ENGLISH);
	
	public String getData();
}
