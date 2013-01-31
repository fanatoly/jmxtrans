package com.googlecode.jmxtrans.model.output;

import com.googlecode.jmxtrans.model.Query;
import com.googlecode.jmxtrans.model.Result;
import com.googlecode.jmxtrans.util.BaseOutputWriter;
import com.googlecode.jmxtrans.util.ValidationException;

import java.util.Map;

/**
 * Outputs query results to stdout in a format compatible with tcollector
 * 
 * @author jon
 */
public class TCollectorOutWriter extends BaseOutputWriter {

    private String tagsString = "";

    @Override
    public void setSettings(Map<String, Object> settings){
        StringBuilder builderOfString = new StringBuilder();
        for(Map.Entry<String, Object> setting : settings.entrySet()){
            builderOfString.append(setting.getKey() + "=" + setting.getValue() + " ");
        }
        tagsString = builderOfString.toString();
        super.setSettings(settings);
    }

    private boolean isNumeric(Object obj){
        return obj instanceof Number;
    }

	/**
	 * nothing to validate
	 */
	public void validateSetup(Query query) throws ValidationException {
	}

	public void doWrite(Query query) throws Exception {
		for (Result r : query.getResults()) {
            for(Map.Entry<String, Object> metric : r.getValues().entrySet()){
                if(isNumeric(metric.getValue()))
                    System.out.println(String.format("%s.%s.%s %d %d %s",
                                                     r.getClassName(),
                                                     r.getAttributeName(),
                                                     metric.getKey(),
                                                     r.getEpoch(),
                                                     metric.getValue(),
                                                     tagsString));
            }

        }
    }
    }
