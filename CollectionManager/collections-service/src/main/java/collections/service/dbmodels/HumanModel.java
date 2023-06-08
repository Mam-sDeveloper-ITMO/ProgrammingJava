package collections.service.dbmodels;

import java.time.LocalDateTime;

import fqme.column.common.BooleanColumn;
import fqme.column.common.DateTimeColumn;
import fqme.column.common.StringColumn;
import fqme.column.common.numeric.DoubleColumn;
import fqme.column.common.numeric.IntegerColumn;
import fqme.column.common.numeric.RealColumn;
import fqme.column.common.numeric.SerialColumn;
import fqme.model.Model;
import fqme.model.reflection.ColumnData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class HumanModel extends Model<HumanModel> {
    @ColumnData
    private Integer id;
    public static final SerialColumn id_ = SerialColumn.of("id").primary();

    @ColumnData
    @NonNull
    private Integer ownerId;
    public static final IntegerColumn ownerId_ = IntegerColumn.of("ownerId").nullable(false);

    @ColumnData
    @NonNull
    private String name;
    public static final StringColumn name_ = StringColumn.of("name").nullable(false);

    @ColumnData
    @NonNull
    private Float xCoordinate;
    public static RealColumn xCoordinate_ = RealColumn.of("xCoordinate").nullable(false);

    @ColumnData
    @NonNull
    private Float yCoordinate;
    public static RealColumn yCoordinate_ = RealColumn.of("yCoordinate").nullable(false);

    @ColumnData
    @NonNull
    private LocalDateTime creationDate;
    public static final DateTimeColumn creationDate_ = DateTimeColumn.of("creationDate").nullable(false);

    @ColumnData
    @NonNull
    private Boolean realHero;
    public static final BooleanColumn realHero_ = BooleanColumn.of("realHero").nullable(false);

    @ColumnData
    @NonNull
    private Boolean hasToothpick;
    public static final BooleanColumn hasToothpick_ = BooleanColumn.of("hasToothpick").nullable(false);

    @ColumnData
    @NonNull
    private Double impactSpeed;
    public static final DoubleColumn impactSpeed_ = DoubleColumn.of("impactSpeed").nullable(false);

    @ColumnData
    @NonNull
    private String soundtrackName;
    public static final StringColumn soundtrackName_ = StringColumn.of("soundtrackName").nullable(false);

    @ColumnData
    private Float minutesOfWaiting;
    public static final RealColumn minutesOfWaiting_ = RealColumn.of("minutesOfWaiting");

    @ColumnData
    private String mood;
    public static final StringColumn mood_ = StringColumn.of("mood");

    @ColumnData
    @NonNull
    private String carName;
    public static final StringColumn carName_ = StringColumn.of("carName").nullable(false);
}
