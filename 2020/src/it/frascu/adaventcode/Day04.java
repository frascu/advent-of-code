package it.frascu.adaventcode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * --- Day 4: Passport Processing ---
 * You arrive at the airport only to realize that you grabbed your North Pole Credentials instead of your passport.
 * While these documents are extremely similar, North Pole Credentials aren't issued by a country
 * and therefore aren't actually valid documentation for travel in most of the world.
 * <p>
 * It seems like you're not the only one having problems, though;
 * a very long line has formed for the automatic passport scanners, and the delay could upset your travel itinerary.
 * <p>
 * Due to some questionable network security, you realize you might be able to solve both of these problems at the same time.
 * <p>
 * The automatic passport scanners are slow because they're having trouble detecting which passports have all required fields.
 * The expected fields are as follows:
 * <p>
 * byr (Birth Year)
 * iyr (Issue Year)
 * eyr (Expiration Year)
 * hgt (Height)
 * hcl (Hair Color)
 * ecl (Eye Color)
 * pid (Passport ID)
 * cid (Country ID)
 * <p>
 * Passport data is validated in batch files (your puzzle input).
 * Each passport is represented as a sequence of key:value pairs separated by spaces or newlines.
 * Passports are separated by blank lines.
 * <p>
 * Here is an example batch file containing four passports:
 * <p>
 * ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
 * byr:1937 iyr:2017 cid:147 hgt:183cm
 * <p>
 * iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
 * hcl:#cfa07d byr:1929
 * <p>
 * hcl:#ae17e1 iyr:2013
 * eyr:2024
 * ecl:brn pid:760753108 byr:1931
 * hgt:179cm
 * <p>
 * hcl:#cfa07d eyr:2025 pid:166559648
 * iyr:2011 ecl:brn hgt:59in
 * <p>
 * The first passport is valid - all eight fields are present. The second passport is invalid - it is missing hgt (the Height field).
 * <p>
 * The third passport is interesting; the only missing field is cid, so it looks like data from North Pole Credentials, not a passport at all!
 * Surely, nobody would mind if you made the system temporarily ignore missing cid fields. Treat this "passport" as valid.
 * <p>
 * The fourth passport is missing two fields, cid and byr. Missing cid is fine, but missing any other field is not, so this passport is invalid.
 * <p>
 * According to the above rules, your improved system would report 2 valid passports.
 * <p>
 * Count the number of valid passports - those that have all required fields. Treat cid as optional. In your batch file, how many passports are valid?
 * <p>
 * --- Part Two ---
 * The line is moving more quickly now, but you overhear airport security talking about how passports with invalid data are getting through.
 * Better add some data validation, quick!
 * <p>
 * You can continue to ignore the cid field, but each other field has strict rules about what values are valid for automatic validation:
 * <p>
 * byr (Birth Year) - four digits; at least 1920 and at most 2002.
 * iyr (Issue Year) - four digits; at least 2010 and at most 2020.
 * eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
 * hgt (Height) - a number followed by either cm or in:
 * If cm, the number must be at least 150 and at most 193.
 * If in, the number must be at least 59 and at most 76.
 * hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
 * ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
 * pid (Passport ID) - a nine-digit number, including leading zeroes.
 * cid (Country ID) - ignored, missing or not.
 * <p>
 * Your job is to count the passports where all required fields are both present and valid according to the above rules. Here are some example values:
 * <p>
 * byr valid:   2002
 * byr invalid: 2003
 * <p>
 * hgt valid:   60in
 * hgt valid:   190cm
 * hgt invalid: 190in
 * hgt invalid: 190
 * <p>
 * hcl valid:   #123abc
 * hcl invalid: #123abz
 * hcl invalid: 123abc
 * <p>
 * ecl valid:   brn
 * ecl invalid: wat
 * <p>
 * pid valid:   000000001
 * pid invalid: 0123456789
 * Here are some invalid passports:
 * <p>
 * eyr:1972 cid:100
 * hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926
 * <p>
 * iyr:2019
 * hcl:#602927 eyr:1967 hgt:170cm
 * ecl:grn pid:012533040 byr:1946
 * <p>
 * hcl:dab227 iyr:2012
 * ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277
 * <p>
 * hgt:59cm ecl:zzz
 * eyr:2038 hcl:74454a iyr:2023
 * pid:3556412378 byr:2007
 * Here are some valid passports:
 * <p>
 * pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
 * hcl:#623a2f
 * <p>
 * eyr:2029 ecl:blu cid:129 byr:1989
 * iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm
 * <p>
 * hcl:#888785
 * hgt:164cm byr:2001 iyr:2015 cid:88
 * pid:545766238 ecl:hzl
 * eyr:2022
 * <p>
 * iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719
 * <p>
 * Count the number of valid passports - those that have all required fields and valid values.
 * Continue to treat cid as optional. In your batch file, how many passports are valid?
 */
public class Day04 {

    private static final String INPUT_FILE_PATH = "input/day04.txt";

    public static void main(String[] args) {
        System.out.println("------ day 04 ------");
        try {
            List<String> lines = Files.readAllLines(new File(INPUT_FILE_PATH).toPath());
            lines.add("");

            List<String> attributes = List.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid");
            Map<String, String> checkMap = new HashMap<>();
            int countValidPartOne = 0;
            int countValidPartTwo = 0;
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    Set<String> checkSet = checkMap.keySet();

                    // calculate part onw
                    if (checkSet.size() == attributes.size()
                            || (checkSet.size() == attributes.size() - 1 && !checkSet.contains("cid"))) {
                        countValidPartOne++;

                        // calculate part two
                        if (isValidPassport(checkMap)) {
                            countValidPartTwo++;
                        }
                    }

                    checkMap = new HashMap<>();
                    continue;
                }

                String[] elements = line.split(" ");
                for (String element : elements) {
                    String[] splitElements = element.split(":");
                    String elementKey = splitElements[0];
                    String elementValue = splitElements[1];
                    checkMap.put(elementKey, elementValue);
                }
            }

            System.out.println("----- part one -----");
            System.out.println(countValidPartOne);

            System.out.println("----- part two -----");
            System.out.println(countValidPartTwo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidPassport(Map<String, String> checkMap) {
        boolean valid = checkMap.get("byr").length() == 4 && hasValidDigits(checkMap.get("byr"), 1920, 2002);
        valid &= checkMap.get("iyr").length() == 4 && hasValidDigits(checkMap.get("iyr"), 2010, 2020);
        valid &= checkMap.get("eyr").length() == 4 && hasValidDigits(checkMap.get("eyr"), 2020, 2030);

        String value = checkMap.get("hgt");
        if (value.endsWith("cm")) {
            valid &= hasValidDigits(value.substring(0, value.indexOf("cm")), 150, 193);
        } else if (value.endsWith("in")) {
            valid &= hasValidDigits(value.substring(0, value.indexOf("in")), 59, 76);
        } else {
            valid = false;
        }

        String hclValue = checkMap.get("hcl").substring(1);
        valid &= checkMap.get("hcl").charAt(0) == '#' && hclValue.length() == 6 && hclValue.matches("^[a-zA-Z0-9]*$");
        valid &= List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(checkMap.get("ecl"));
        valid &= checkMap.get("pid").length() == 9 && checkMap.get("pid").matches("^[0-9]*$");
        return valid;
    }


    private static boolean hasValidDigits(String value, int min, int max) {
        int number = Integer.parseInt(value);
        if (number < min || number > max) {
            return false;
        }
        return true;
    }

}
