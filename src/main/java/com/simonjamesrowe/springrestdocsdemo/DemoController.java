package com.simonjamesrowe.springrestdocsdemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class DemoController {

    @PostMapping("/api/v1/entity/similar")
    public List<DemoEntity> similarEntities(@RequestBody DemoEntity demoEntity) {
        return IntStream.range(1,5)
                .boxed()
                .map(i -> new DemoEntity(i, demoEntity.getaString(),demoEntity.getValues()))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v1/entity/")
    public List<DemoEntity> all() {
        DemoEntity demoEntity = new DemoEntity(null, "Str Value", Arrays.asList("val1", "val2", "val3"));
        return IntStream.range(1,5)
                .boxed()
                .map(i -> new DemoEntity(i, demoEntity.getaString(),demoEntity.getValues()))
                .collect(Collectors.toList());
    }
}
