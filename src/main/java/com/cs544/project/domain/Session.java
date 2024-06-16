package com.cs544.project.domain;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Session implements Comparable<Session> {
    private LocalDate date;
    private Period morningPeriod = new Period(LocalTime.of(10, 0), LocalTime.of(12, 30));
    private Period afternoonPeriod = new Period(LocalTime.of(13, 30), LocalTime.of(15, 30));

    public Session() {
    }

    public Session(LocalDate date) {
        this.date = date;
    }

    @Override
    public int compareTo(Session o) {
        return this.date.compareTo(o.getDate());
    }

    @Data
    static class Period {
        private boolean isPresent;
        private LocalTime startTime;
        private LocalTime endTime;

        public Period() {
        }

        public Period(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
}
