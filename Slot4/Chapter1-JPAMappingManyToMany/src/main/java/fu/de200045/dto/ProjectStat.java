package fu.de200045.dto;

import java.math.BigDecimal;

public record ProjectStat(String projectName, Long activeCount, BigDecimal totalSalary) {

}
