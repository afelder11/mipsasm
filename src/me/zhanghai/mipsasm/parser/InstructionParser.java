/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.parser;

import me.zhanghai.mipsasm.instruction.Instruction;
import me.zhanghai.mipsasm.instruction.Operation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InstructionParser {

    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile(
            "([a-zA-Z]+)(?:\\s+([0-9a-zA-Z$]+)(?:\\s*,\\s*([0-9a-zA-Z$]+))*)?");

    private static final ThreadLocal<Matcher> INSTRUCTION_MATCHER = new ThreadLocal<Matcher>() {
        @Override
        protected Matcher initialValue() {
            return INSTRUCTION_PATTERN.matcher("");
        }
    };

    private InstructionParser() {}

    public static Instruction parse(String instructionString) {

        instructionString = instructionString.trim();

        Matcher matcher = INSTRUCTION_MATCHER.get();
        matcher.reset(instructionString);
        if (!matcher.matches()) {
            throw new InvalidInstructionException("Instruction: " + instructionString);
        }

        if (matcher.groupCount() < 1) {
            throw new MissingOperationException();
        }

        String operationName = matcher.group(1);
        Operation operation;
        try {
            operation = Operation.valueOf(operationName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NoSuchOperationException("Operation: " + operationName);
        }

        int operandCount = matcher.groupCount() - 1;
        String[] operands = new String[operandCount];
        for (int i = 0; i < operandCount; ++i) {
            operands[i] = matcher.group(i + 2);
        }


        // TODO:
        return null;
    }
}
