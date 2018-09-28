package com.crediline.utils;

import com.crediline.model.*;

import java.io.File;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Created by dimer on 3/23/14.
 */
public class PrintUtils {

    //ToDo: must be finished for all parameters
    public static String print(Address address, PrintFlag printFlag) {
        Locale defaultLocale = LocaleUtils.getCurrentLocale();
        StringBuilder sb = new StringBuilder();
        if (printFlag.equals(PrintFlag.PRINT_MACHINE)) {
            sb.append(Address.class.toString().concat(": "));
        }
        if ("bg".equals(defaultLocale.getLanguage())) {
            try {

                if (address.getCountry() != null && address.getCountry().length() > 0) {
                    sb.append(address.getCountry().concat(", "));
                }

                if (address.getCity() != null && address.getCity().toString().length() > 0) {
                    sb.append(address.getCity().toString().concat(", "));
                }

                if (address.getQuarter() != null && address.getQuarter().length() > 0) {
                    sb.append(LocaleUtils.getLocaliziedMessage("common.quarter").concat(" ").concat(address.getQuarter()).concat(", "));
                }

                if (address.getStreet() != null && address.getStreet().toString().length() > 0) {
                    sb.append(LocaleUtils.getLocaliziedMessage("common.street").concat(" ").concat(address.getStreet().toString()).concat(", "));
                }

                if (address.getNumber() != null && address.getNumber().length() > 0) {
                    sb.append(LocaleUtils.getLocaliziedMessage("common.No").concat(" ").concat(address.getNumber()).concat(", "));
                }

                if (address.getFloor() != null && address.getFloor().length() > 0) {
                    sb.append(LocaleUtils.getLocaliziedMessage("common.floor").concat(" ").concat(String.valueOf(address.getFloor())).concat(", "));
                }

                if (address.getApartment() != null && address.getApartment().length() > 0) {
                    sb.append(LocaleUtils.getLocaliziedMessage("common.apartment").concat(" ").concat(String.valueOf(address.getApartment())).concat(", "));
                }

            } catch (Exception e) {
                return sb.toString();
            }
        }
        return sb.toString();
    }

    public static String print(Address address, PrintFlag printFlag, String printSeverity) {
        Locale defaultLocale = LocaleUtils.getCurrentLocale();
        StringBuilder sb = new StringBuilder();
        if (printFlag.equals(PrintFlag.PRINT_MACHINE)) {
            sb.append(Address.class.toString().concat(": "));
        }

        Set<String> arguments = new HashSet<>(Arrays.asList(printSeverity.split(",")));

        if ("bg".equals(defaultLocale.getLanguage())) {
            try {
                if (address.getCountry() != null && address.getCountry().length() > 0 && !arguments.contains("woCountry")) {
                    sb.append(address.getCountry().concat(", "));
                }

                if (address.getCity() != null && address.getCity().toString().length() > 0 && !arguments.contains("woCity")) {
                    sb.append(address.getCity().toString().concat(", "));
                }

                if (address.getQuarter() != null && address.getQuarter().length() > 0) {
                    sb.append(LocaleUtils.getLocaliziedMessage("common.quarter").concat(" ").concat(address.getQuarter()).concat(", "));
                }

                if (address.getStreet() != null && address.getStreet().toString().length() > 0) {
                    sb.append(LocaleUtils.getLocaliziedMessage("common.street").concat(" ").concat(address.getStreet().toString()).concat(", "));
                }

                if (address.getNumber() != null && address.getNumber().length() > 0) {
                    sb.append(LocaleUtils.getLocaliziedMessage("common.No").concat(" ").concat(address.getNumber()).concat(", "));
                }

                if (address.getFloor() != null && address.getFloor().length() > 0) {
                    sb.append(LocaleUtils.getLocaliziedMessage("common.floor").concat(" ").concat(String.valueOf(address.getFloor())).concat(", "));
                }

                if (address.getApartment() != null && address.getApartment().length() > 0) {
                    sb.append(LocaleUtils.getLocaliziedMessage("common.apartment").concat(" ").concat(String.valueOf(address.getApartment())).concat(", "));
                }

            } catch (Exception e) {
                return sb.toString();
            }
        }
        return sb.toString();
    }

    public static String print(Province province, PrintFlag printFlag) {
        Locale defaultLocale = LocaleUtils.getCurrentLocale();
        StringBuilder sb = new StringBuilder();
        if (printFlag.equals(PrintFlag.PRINT_MACHINE)) {
            sb.append(Address.class.toString().concat(": "));
        }
        if ("bg".equals(defaultLocale.getLanguage())) {
            try {
                if (province.getName() != null && province.getName().length() > 0) {
                    sb.append(province.getName());
                }

            } catch (Exception e) {
                return sb.toString();
            }
        }
        return sb.toString();
    }

    //ToDo: must be finished for all parameters
    public static String print(Person person) {
        Locale defaultLocale = LocaleUtils.getCurrentLocale();
        StringBuilder sb = new StringBuilder(Person.class.toString().concat(": "));
        if ("bg".equals(defaultLocale.getLanguage())) {
            try {
                sb.append(person.getName().concat(", "));
                sb.append(person.getMidname().concat(", "));
                sb.append(person.getSurname().concat(", "));
                sb.append(person.getEgn().concat(", "));
                for (Address address : person.getAddresses()) {
                    sb.append(LocaleUtils.getLocaliziedMessage("address.addressType.".concat(address.getAddressType().toString())));
                    sb.append(":".concat(address.toString().concat("\n")));
                }
            } catch (Exception e) {
                return sb.toString();
            }
        }
        return sb.toString();
    }

    //ToDo: must be finished for all parameters
    public static String print(Credit credit) {
        Locale defaultLocale = LocaleUtils.getCurrentLocale();
        StringBuilder sb = new StringBuilder(Credit.class.toString().concat(": "));
        if ("bg".equals(defaultLocale.getLanguage())) {
            try {
                sb.append(credit.getId().toString().concat(", "));
                sb.append(credit.getBasis().toString().concat(", "));
                sb.append(credit.getInterest().toString().concat(", "));
                sb.append(credit.getPeriod().toString().concat(", "));
                if (credit.getPerson() != null) {
                    sb.append(" credit owner: ".concat(print(credit.getPerson())));
                }
            } catch (Exception e) {
                return sb.toString();
            }
        }
        return sb.toString();
    }

    //ToDo: must be finished for all parameters
    public static String print(Transaction income) {
        Locale defaultLocale = LocaleUtils.getCurrentLocale();
        StringBuilder sb = new StringBuilder(Transaction.class.toString().concat(": "));
        if ("bg".equals(defaultLocale.getLanguage())) {
            try {
                sb.append(income.getId().toString().concat(", "));
                sb.append(income.getSum().toString().concat(", "));
                /*sb.append(income.getPerson().toString());*/
            } catch (Exception e) {
                return sb.toString();
            }
        }
        return sb.toString();
    }

    /*public static String print(Outcome outcome) {
        Locale defaultLocale = LocaleUtils.getCurrentLocale();
        StringBuilder sb = new StringBuilder(Outcome.class.toString().concat(": "));
        if ("bg".equals(defaultLocale.getLanguage())) {
            try {
                sb.append(outcome.getId().toString().concat(", "));
                sb.append(outcome.getSum().toString().concat(", "));
                sb.append(outcome.getReason().toString());
            } catch (Exception e) {
                return sb.toString();
            }
        }
        return sb.toString();
    }*/

    public static String print(Payment payment) {
        Locale defaultLocale = LocaleUtils.getCurrentLocale();
        StringBuilder sb = new StringBuilder(Payment.class.toString().concat(": "));
        if ("bg".equals(defaultLocale.getLanguage())) {
            try {
                sb.append(payment.getId().toString().concat(", "));
                sb.append(payment.getSum().toString().concat(", "));
                sb.append(payment.getBasis().toString().concat(", "));
                sb.append(payment.getInterest().toString());
            } catch (Exception e) {
                return sb.toString();
            }
        }
        return sb.toString();
    }

    public static String print(User user) {
        Locale defaultLocale = LocaleUtils.getCurrentLocale();
        StringBuilder sb = new StringBuilder(User.class.toString().concat(": "));
        if ("bg".equals(defaultLocale.getLanguage())) {
            try {
                sb.append(user.getId().toString().concat(", "));
                sb.append(user.getUsername().concat(", "));
                sb.append(user.getRole().toString().concat(", "));
                if (user.getOffice() != null) {
                    sb.append(user.getOffice().toString());
                }
            } catch (Exception e) {
                return sb.toString();
            }
        }
        return sb.toString();
    }

    public static String print(Event event) {
        Locale defaultLocale = LocaleUtils.getCurrentLocale();
        StringBuilder sb = new StringBuilder(Event.class.toString().concat(": "));
        if ("bg".equals(defaultLocale.getLanguage())) {
            try {
                sb.append(event.getId().toString().concat(", "));
                sb.append(event.getValue().concat(", "));
            } catch (Exception e) {
                return sb.toString();
            }
        }
        return sb.toString();
    }

    public static String print(Office office) {
        Locale defaultLocale = LocaleUtils.getCurrentLocale();
        StringBuilder sb = new StringBuilder(Office.class.toString().concat(": "));
        if ("bg".equals(defaultLocale.getLanguage())) {
            try {
                sb.append(office.getId().toString().concat(", "));
                sb.append(office.getName().concat(", "));
                sb.append(office.getRegion().toString().concat(", "));
                sb.append(office.getAddress().toString().concat(", "));
                for (Phone phone : office.getPhones()) {
                    sb.append(phone.toString().concat(", "));
                }
            } catch (Exception e) {
                return sb.toString();
            }
        }
        return sb.toString();
    }

    public static String print(Region region) {
        Locale defaultLocale = LocaleUtils.getCurrentLocale();
        StringBuilder sb = new StringBuilder(Region.class.toString().concat(": "));
        if ("bg".equals(defaultLocale.getLanguage())) {
            try {
                sb.append(region.getId().toString().concat(", "));
                sb.append(region.getName());
            } catch (Exception e) {
                return sb.toString();
            }
        }
        return sb.toString();
    }

    //STODO take a look on rounding for example if the number is 12,666666666
    public static String print(BigDecimal bigDecimal) {
        BigDecimal bigDecimalFormatted = null;
        bigDecimalFormatted = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(0);
        decimalFormat.setGroupingUsed(false);
        String result = decimalFormat.format(bigDecimalFormatted);

        return result;
    }

    //STODO take a look on rounding for example if the number is 12,666666666
    public static String print(BigDecimal bigDecimal, Locale locale) {
        BigDecimal bigDecimalFormatted = null;
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        DecimalFormat decimalFormat = (DecimalFormat) nf;
        bigDecimalFormatted = bigDecimal.setScale(2, BigDecimal.ROUND_DOWN);
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(0);
        decimalFormat.setGroupingUsed(false);
        String result = decimalFormat.format(bigDecimalFormatted);


        return result;
    }

    public static String print(City city, PrintFlag printFlag) {
        Locale defaultLocale = LocaleUtils.getCurrentLocale();
        StringBuilder sb = new StringBuilder();

        if (printFlag.equals(PrintFlag.PRINT_MACHINE)) {
            sb.append(Address.class.toString().concat(": "));
        }

        if ("bg".equals(defaultLocale.getLanguage())) {
            try {
                sb.append(city.getName().concat(" "));
                sb.append(LocaleUtils.getLocaliziedMessage("common.obshtina").concat(" "));
                sb.append(city.getObshtina().concat(" "));
                sb.append(LocaleUtils.getLocaliziedMessage("common.oblast").concat(" "));
                sb.append(city.getOblast().concat(" "));
            } catch (Exception e) {
                return sb.toString();
            }
        }
        return sb.toString();
    }
    
    public static void printExceptionInFile(Exception exception, String filename) {
    	File file = new File(filename);
		PrintStream ps = null;
		try {
			ps = new PrintStream(file);
			exception.printStackTrace(ps);
		} catch (Exception ex) {
		    ex.printStackTrace(ps);
		}
		ps.close();
    }
}
