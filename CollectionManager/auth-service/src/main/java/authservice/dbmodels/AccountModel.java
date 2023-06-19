package authservice.dbmodels;

import java.time.LocalDateTime;

import fqme.column.common.DateTimeColumn;
import fqme.column.common.StringColumn;
import fqme.column.common.numeric.SerialColumn;
import fqme.model.Model;
import fqme.model.reflection.ColumnData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AccountModel extends Model<AccountModel> {
    @ColumnData
    private Integer id;
    public static final SerialColumn id_ = SerialColumn.of("id").primary();

    @ColumnData
    @NonNull
    private String login;
    public static final StringColumn login_ = StringColumn.of("login").nullable(false);

    @ColumnData
    @NonNull
    private String password;
    public static final StringColumn password_ = StringColumn.of("password").nullable(false);

    @ColumnData
    @NonNull
    private String salt;
    public static final StringColumn salt_ = StringColumn.of("salt").nullable(false);

    @ColumnData
    @Getter
    @Setter
    private String token;
    public static final StringColumn token_ = StringColumn.of("token").nullable(true);

    @ColumnData
    @Getter
    @Setter
    private LocalDateTime tokenExpireAt;
    public static final DateTimeColumn tokenExpireAt_ = DateTimeColumn.of("tokenExpireAt").nullable(true);
}
