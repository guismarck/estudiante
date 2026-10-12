package app.estudiante.repositorio;

import java.math.BigDecimal;

public interface PagoSaldoProjection {
    Integer getIdTarifa();
    String getConcepto();
    Integer getAnioLectivo();
    BigDecimal getMontoTarifa();
    BigDecimal getMontoPagado();
    BigDecimal getSaldoRestante();
}
