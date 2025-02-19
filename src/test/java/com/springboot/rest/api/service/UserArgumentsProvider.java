package com.springboot.rest.api.service;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import com.springboot.rest.api.entity.User;

public class UserArgumentsProvider implements ArgumentsProvider{

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {

        return Stream.of(
                Arguments.of(User.builder().userName("anwar").password("jan").build()),
                Arguments.of(User.builder().userName("anshu").password("anshu").build())
        );

    }

}
