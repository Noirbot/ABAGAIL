package shared.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import shared.DataSet;
import shared.DataSetDescription;
import shared.Instance;

/**
 * Class to read in data from a CSV file where the index (from 0) of the label is known
 * @author Tim Swihart <https://github.com/chronoslynx>
 * @date 2013-03-05
 */
public class CSVDataSetReaderWithLabel extends DataSetReader {
	private int labelIndex;
	
	public CSVDataSetReaderWithLabel(String file, int labelIndex) {
		super(file);
		this.labelIndex = labelIndex;
	}

    /**
     * Read the CSV file and create the Instances with their associated labels
     * @return the data 
     */
    public DataSet read() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        List<Instance> data = new ArrayList<Instance>();
        Pattern pattern = Pattern.compile("[ ,]+");
        while ((line = br.readLine()) != null) {
            List<String> split = new ArrayList<String>(Arrays.asList(pattern.split(line.trim())));

            // Grab the label (here as an integer) and then remove it from our split set
            int label = Integer.parseInt(split.remove(labelIndex));
            
            double[] input = new double[split.size()];
            for (int i = 0; i < input.length; i++) {
                input[i] = Double.parseDouble(split.get(i));
            }
            
            Instance instance = new Instance(input, label);
            data.add(instance);
        }
        br.close();
        Instance[] instances = (Instance[]) data.toArray(new Instance[0]);

        DataSet set = new DataSet(instances);
        
        DataSet labelSet = set.getLabelDataSet();
        DataSetDescription labelDescription = new DataSetDescription(labelSet);
        set.setDescription(new DataSetDescription(set));
        set.getDescription().setLabelDescription(labelDescription);

        return set;
    }
}
