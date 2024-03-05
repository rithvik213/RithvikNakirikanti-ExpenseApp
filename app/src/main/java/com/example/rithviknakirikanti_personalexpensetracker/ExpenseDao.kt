import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rithviknakirikanti_personalexpensetracker.Expense

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insert(expense: Expense)

    @Query("SELECT * FROM expense ORDER BY date DESC")
    fun getAllExpenses(): LiveData<List<Expense>>

    @Query("SELECT * FROM expense WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getExpensesByDate(startDate: Long, endDate: Long): LiveData<List<Expense>>

    @Query("SELECT * FROM expense WHERE category = :category ORDER BY date DESC")
    fun getExpensesByCategory(category: String): LiveData<List<Expense>>
}
