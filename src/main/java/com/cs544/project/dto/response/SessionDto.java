package com.cs544.project.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class SessionDto  {
    private LocalDate date;
    private PeriodDto morningPeriod = new PeriodDto(LocalTime.of(10, 0), LocalTime.of(12, 30));
    private PeriodDto afternoonPeriod = new PeriodDto(LocalTime.of(13, 30), LocalTime.of(15, 30));

    public SessionDto() {
    }

    public SessionDto(LocalDate date) {
        this.date = date;
    }


    @Data
    static class PeriodDto {
        private boolean isPresent;
        private LocalTime startTime;
        private LocalTime endTime;

        public PeriodDto() {
        }

        public PeriodDto(LocalTime startTime, LocalTime endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
}
