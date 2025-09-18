package com.TCC.DTO;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record HabitoDTO(String titulo, LocalTime horario, DayOfWeek diaSemana) {
    
}
