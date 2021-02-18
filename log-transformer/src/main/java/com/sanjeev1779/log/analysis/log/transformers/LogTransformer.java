package com.sanjeev1779.log.analysis.log.transformers;

import com.sanjeev1779.log.analysis.common.dtos.LogMessageDto;

public interface LogTransformer {
    boolean process(LogMessageDto logMessageDto);
}
