
package com.mycompany.advertisementproject.Tools;

import com.mycompany.advertisementproject.entities.Advertisement;
import java.util.Comparator;

public class AdvertComparator implements Comparator<Advertisement> {

        private String attribute;
        private boolean ascending;

        public AdvertComparator(String attribute, boolean ascending) {
            this.attribute = attribute;
            this.ascending = ascending;
        }

        @Override
        public int compare(Advertisement a1, Advertisement a2) {
            if (ascending) {
                return compareAscending(a1, a2);
            } else {
                return compareDescending(a1, a2);
            }
        }

        private int compareAscending(Advertisement a1, Advertisement a2) {
            switch (attribute) {
                case "DATE": {
                    return a1.getRegistrationDate().compareTo(a2.getRegistrationDate());
                }
                case "PRICE": {
                    return a1.getPrice().compareTo(a2.getPrice());
                }
            }
            return a1.getId().compareTo(a2.getId());
        }

        private int compareDescending(Advertisement a1, Advertisement a2) {
            switch (attribute) {
                case "DATE": {
                    return a2.getRegistrationDate().compareTo(a1.getRegistrationDate());
                }
                case "PRICE": {
                    return a2.getPrice().compareTo(a1.getPrice());
                }
            }
            return a1.getId().compareTo(a2.getId());
        }
    }
