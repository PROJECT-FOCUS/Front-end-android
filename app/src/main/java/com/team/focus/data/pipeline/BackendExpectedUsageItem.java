package com.team.focus.data.pipeline;

import java.time.Duration;

public class BackendExpectedUsageItem {

    private final String appId;
    private final Duration usage;

    public BackendExpectedUsageItem(String id, Duration use)	{
        appId = id;
        usage = use;
    }

    public BackendExpectedUsageItem(String id, String usageStr)	{
        appId = id;
        usage = Duration.ofSeconds(DurationHelper.getSecondsFromFormattedDuration(usageStr));;
    }

    public String getAppId()	{
        return appId;
    }

    public Duration getUsage()	{
        return usage;
    }

}
