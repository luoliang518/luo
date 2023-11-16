package com.luo.task;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.Arrays;

public class WordCountJob {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<String> dataSource = env.readTextFile("D:\\luo\\task\\src\\main\\resources\\wordcount.txt");
        FlatMapOperator<String, Tuple2<String, Integer>> flatMap = dataSource.flatMap((FlatMapFunction<String, Tuple2<String, Integer>>) (lines, out) -> {
            Arrays.stream(lines.split(" ")).forEach(s -> out.collect(Tuple2.of(s, 1)));
        }).returns(Types.TUPLE(Types.STRING, Types.INT));
        AggregateOperator<Tuple2<String, Integer>> sum = flatMap.groupBy(0).sum(1);
        sum.print();
    }
}