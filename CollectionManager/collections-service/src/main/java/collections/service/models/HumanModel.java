package collections.service.models;

import fqme.column.common.numeric.SerialColumn;
import fqme.model.Model;
import fqme.model.reflection.ColumnData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class HumanModel extends Model<HumanModel> {
    @ColumnData
    private Integer id;
    public static final SerialColumn id_ = SerialColumn.of("id").primary();
}
