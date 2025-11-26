import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class CollectionOfFunctionsTest {
    
    private CollectionOfFunctions cof;
    
    @BeforeEach
    public void setUp() {
        cof = new CollectionOfFunctions();
    }
    
    // Tests for gradeAssigner()     
    @Test
    public void testGradeAssigner_GradeA() {
        assertEquals('A', cof.gradeAssigner(95));
    }
    
    @Test
    public void testGradeAssigner_GradeB() {
        assertEquals('B', cof.gradeAssigner(85));
    }
    
    @Test
    public void testGradeAssigner_GradeC() {
        assertEquals('C', cof.gradeAssigner(75));
    }
    
    @Test
    public void testGradeAssigner_GradeD() {
        assertEquals('D', cof.gradeAssigner(65));
    }
    
    @Test
    public void testGradeAssigner_GradeF() {
        assertEquals('F', cof.gradeAssigner(50));
    }
    
    @Test
    public void testGradeAssigner_BoundaryA() {
        assertEquals('A', cof.gradeAssigner(90));
    }
    
    @Test
    public void testGradeAssigner_BoundaryB() {
        assertEquals('B', cof.gradeAssigner(89));
    }
    
    // Tests for sortArray()     
    @Test
    public void testSortArray_UnsortedArray() {
        int[] nums = {5, 2, 8, 1, 9};
        cof.sortArray(nums);
        assertArrayEquals(new int[]{1, 2, 5, 8, 9}, nums);
    }
    
    @Test
    public void testSortArray_AlreadySorted() {
        int[] nums = {1, 2, 3, 4, 5};
        cof.sortArray(nums);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, nums);
    }
    
    @Test
    public void testSortArray_ReverseSorted() {
        int[] nums = {5, 4, 3, 2, 1};
        cof.sortArray(nums);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, nums);
    }
    
    @Test
    public void testSortArray_WithNegativeNumbers() {
        int[] nums = {-3, 5, -1, 0, 2};
        cof.sortArray(nums);
        assertArrayEquals(new int[]{-3, -1, 0, 2, 5}, nums);
    }
    
    @Test
    public void testSortArray_WithDuplicates() {
        int[] nums = {3, 1, 3, 2, 1};
        cof.sortArray(nums);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3}, nums);
    }
    
    // Tests for rowSum()     
    @Test
    public void testRowSum_FirstRow() {
        int[][] nums = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertEquals(6, cof.rowSum(nums, 0));
    }
    
    @Test
    public void testRowSum_MiddleRow() {
        int[][] nums = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertEquals(15, cof.rowSum(nums, 1));
    }
    
    @Test
    public void testRowSum_LastRow() {
        int[][] nums = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertEquals(24, cof.rowSum(nums, 2));
    }
    
    @Test
    public void testRowSum_WithNegativeNumbers() {
        int[][] nums = {{-1, -2, -3}, {4, 5, 6}};
        assertEquals(-6, cof.rowSum(nums, 0));
    }
    
    @Test
    public void testRowSum_WithZeros() {
        int[][] nums = {{0, 0, 0}, {1, 2, 3}};
        assertEquals(0, cof.rowSum(nums, 0));
    }
    
    // Tests for minimumValue()     
    @Test
    public void testMinimumValue_PositiveNumbers() {
        int[] nums = {5, 2, 8, 1, 9};
        int[] result = cof.minimumValue(nums);
        assertArrayEquals(new int[]{1, 3}, result);
    }
    
    @Test
    public void testMinimumValue_MinAtStart() {
        int[] nums = {1, 2, 3, 4, 5};
        int[] result = cof.minimumValue(nums);
        assertArrayEquals(new int[]{1, 0}, result);
    }
    
    @Test
    public void testMinimumValue_MinAtEnd() {
        int[] nums = {5, 4, 3, 2, 1};
        int[] result = cof.minimumValue(nums);
        assertArrayEquals(new int[]{1, 4}, result);
    }
    
    @Test
    public void testMinimumValue_WithNegativeNumbers() {
        int[] nums = {3, -5, 2, -1, 0};
        int[] result = cof.minimumValue(nums);
        assertArrayEquals(new int[]{-5, 1}, result);
    }
    
    @Test
    public void testMinimumValue_AllSameValues() {
        int[] nums = {5, 5, 5, 5};
        int[] result = cof.minimumValue(nums);
        assertArrayEquals(new int[]{5, 0}, result);
    }
    
    // Tests for stringCleaner()     
    @Test
    public void testStringCleaner_RemoveVowel() {
        assertEquals("Hllo", cof.stringCleaner("Hello", 'e'));
    }
    
    @Test
    public void testStringCleaner_RemoveSpace() {
        assertEquals("HelloWorld", cof.stringCleaner("Hello World", ' '));
    }
    
    @Test
    public void testStringCleaner_CharNotInString() {
        assertEquals("Hello", cof.stringCleaner("Hello", 'z'));
    }
    
    @Test
    public void testStringCleaner_RemoveAllOccurrences() {
        assertEquals("Heo Word", cof.stringCleaner("Hello World", 'l'));
    }
    
    @Test
    public void testStringCleaner_EmptyResult() {
        assertEquals("", cof.stringCleaner("aaaa", 'a'));
    }
}

