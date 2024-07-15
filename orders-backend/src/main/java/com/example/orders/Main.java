package com.example.orders;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {
        List<Map<String, Object>> listOfMaps = new ArrayList<>();

        ReasonCode reason1 = new ReasonCode("active", "001", "Description 1");
        ReasonCode reason2 = new ReasonCode("inactive", "002", "Description 2");
        ReasonCode reason3 = new ReasonCode("active", "001", "Description 1");

        Map<String, Object> map1 = new HashMap<>();
        map1.put("postalAddress", "123 Main St");
        map1.put("reasonCode", reason1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("postalAddress", "456 Oak St");
        map2.put("reasonCode", reason2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("postalAddress", "123 Main St");
        map3.put("reasonCode", reason1);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("postalAddress", "789 Pine St");
        map4.put("reasonCode", reason3);

        Map<String, Object> map5 = new HashMap<>();
        map5.put("postalAddress", "789 Pine St");
        map5.put("reasonCode", reason3);

        listOfMaps.add(map1);
        listOfMaps.add(map2);
        listOfMaps.add(map3);
        listOfMaps.add(map4);
        listOfMaps.add(map5);

        List<Map<String, Object>> uniqueMaps = listOfMaps.stream()
                .filter(distinctByKeys("postalAddress","reasonCode"))
                .collect(Collectors.toList());

        uniqueMaps.forEach(System.out::println);
    }

    public static Predicate<Map<String, Object>> distinctByKeys(String... keys) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return map -> {
            List<Object> keyValues = Arrays.stream(keys)
                    .map(key -> key.equals("reasonCode") ?
                                 ((ReasonCode) map.get(key)).getReasonCode() : map.get(key))
                    .collect(Collectors.toList());
            return seen.add(keyValues);
        };
    }

    static class ReasonCode {
        private String status;
        private String reasonCode;
        private String description;

        public ReasonCode(String status, String reasonCode, String description) {
            this.status = status;
            this.reasonCode = reasonCode;
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReasonCode() {
            return reasonCode;
        }

        public void setReasonCode(String reasonCode) {
            this.reasonCode = reasonCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ReasonCode that = (ReasonCode) o;
            return Objects.equals(status, that.status) &&
                    Objects.equals(reasonCode, that.reasonCode) &&
                    Objects.equals(description, that.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(status, reasonCode, description);
        }

        @Override
        public String toString() {
            return "ReasonCode{" +
                    "status='" + status + '\'' +
                    ", reasonCode='" + reasonCode + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
