package com.team.focus.data.pipeline;

import java.time.Duration;

public class BackendActualUsageItem {

    private final String appId;
    private final Duration usage;

    public BackendActualUsageItem(String id, Duration use)	{
        appId = id;
        usage = use;
    }

    public BackendActualUsageItem(String id, String usageStr)	{
        appId = id;
        usage = Duration.ofSeconds(DurationHelper.getSecondsFromFormattedDuration(usageStr));
    }

    public String getAppId()	{
        return appId;
    }

    public Duration getUsage()	{
        return usage;
    }

}
