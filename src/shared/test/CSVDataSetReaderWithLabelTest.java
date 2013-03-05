package shared.test;

import java.io.File;

import shared.DataSet;
import shared.DataSetDescription;
import shared.reader.CSVDataSetReader;
import shared.reader.CSVDataSetReaderWithLabel;
import shared.reader.DataSetReader;
import shared.filt.ContinuousToDiscreteFilter;
import shared.filt.LabelSplitFilter;

/**
 * Testing CSVDataSetReaderWithLabel
 * @author Tim Swihart <https://github.com/chronoslynx>
 * @version 1.0
 */
public class CSVDataSetReaderWithLabelTest {
    /**
     * The test main
     * @param args ignored parameters
     */
    public static void main(String[] args) throws Exception {
    	String fp = (new File("").getAbsolutePath()) + "/src/shared/test/abalone_label_at_0.data";
        DataSetReader dsr = new CSVDataSetReaderWithLabel(fp, 0);
        // read in the raw data
        DataSet ds = dsr.read();
        
        ContinuousToDiscreteFilter ctdf = new ContinuousToDiscreteFilter(10);
        ctdf.filter(ds);
        //System.out.println(ds);
        
        // There is an issue with the filters removing the datasetdescription.
        System.out.println(ds.getDescription());
    }
}
