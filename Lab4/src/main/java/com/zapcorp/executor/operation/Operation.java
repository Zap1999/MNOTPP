package com.zapcorp.executor.operation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Operation {
    CPU("CpuBoundOp"), IO("IoBoundOp"), MEMORY("MemoryBoundOp");

    @Getter
    private final String cls;
}
