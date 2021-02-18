package com.sanjeev1779.log.analysis.log.transformers;

import com.sanjeev1779.log.analysis.common.dtos.LogMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultLogTransformer implements LogTransformer {
    Logger logger = LoggerFactory.getLogger(DefaultLogTransformer.class);

    @Override
    public boolean process(LogMessageDto logMessageDto) {

        return false;
    }
}
