package com.crediline.utils;

import java.util.prefs.Preferences;

public class PreferencesUtils {
	private static final String SMALL_CREDIT_FEE_KEY = "SMALL_CREDIT_FEE_KEY";
	private static final String BIG_CREDIT_FEE_KEY = "BIG_CREDIT_FEE_KEY";

	public static int getSmallCreditFeePreference() {
		Preferences prefs = Preferences.userNodeForPackage(PreferencesUtils.class);

		return prefs.getInt(SMALL_CREDIT_FEE_KEY, 2);
	}

	public static void setSmallCreditFeePreference(int preference) {
		Preferences prefs = Preferences.userNodeForPackage(PreferencesUtils.class);

		prefs.putInt(SMALL_CREDIT_FEE_KEY, preference);
	}
	
	public static int getBigCreditFeePreference() {
		Preferences prefs = Preferences.userNodeForPackage(PreferencesUtils.class);

		return prefs.getInt(BIG_CREDIT_FEE_KEY, 20);
	}
	
	public static void setBigCreditFeePreference(int preference) {
		Preferences prefs = Preferences.userNodeForPackage(PreferencesUtils.class);

		prefs.putInt(BIG_CREDIT_FEE_KEY, preference);
	}
}
