package com.ganzhi.domain.ai;

import com.ganzhi.domain.DataCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToAi {

    private DataCollection dataCollection;

    private String algorithm_docker;
}
