package com.example.demo;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

class GreetingFailureAnalyzer extends AbstractFailureAnalyzer<GreetingTemplateMissing> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, GreetingTemplateMissing cause) {
        return new FailureAnalysis(
                "Greeting template undefined.",
                "Define greeting.template property.",
                rootFailure);
    }
}
