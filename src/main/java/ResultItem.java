/**
 * Wrapper class that combines a GradeResults object with UI state (checkbox selection).
 * This keeps the data model (GradeResults) purely data-focused while adding UI-specific metadata.
 */
public class ResultItem {
    private final GradeResults gradeResults;
    private boolean selected;

    public ResultItem(GradeResults gradeResults) {
        this.gradeResults = gradeResults;
        this.selected = false;
    }

    public GradeResults getGradeResults() {
        return gradeResults;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return gradeResults.toString();
    }
}
