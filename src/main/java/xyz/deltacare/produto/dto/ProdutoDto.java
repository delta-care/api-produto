package xyz.deltacare.produto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDto implements Serializable {

    private String id;

    @NotBlank(message = "O plano deve ser informado.")
    @Size(max = 255)
    private String plano;

    @NotBlank(message = "O subplano deve ser informado.")
    @Size(max = 255)
    private String subplano;

    @NotNull(message = "O valor deve ser informado.")
    private BigDecimal valor;

    @NotNull(message = "O data de início de vigência deve ser informada.")
    @PastOrPresent
    @JsonProperty("data_inicio_vigencia")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dataInicioVigencia;

    @JsonProperty("data_fim_vigencia")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dataFimVigencia;

}