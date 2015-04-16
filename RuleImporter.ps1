param([string]$pathToInspectCodeExe)
# Export issues here
& $pathToInspectCodeExe /it /no-buildin-settings /o=inspectCodeRules.xml

$priorityMap = @{ "DO_NOT_SHOW" = "INFO"; "HINT" = "INFO"; "SUGGESTION" = "MINOR"; "WARNING" = "MAJOR"; "ERROR" = "BLOCKER";}

[xml]$exported = Get-Content .\inspectCodeRules.xml
$rulesMap = @{ 
    "JS" = New-Object System.XMl.XmlTextWriter(((Convert-Path $PSScriptRoot) + "\src\main\resources\org\sonar\plugins\resharper\rules-js.xml"), $Null);
    "CS" = New-Object System.XMl.XmlTextWriter(((Convert-Path $PSScriptRoot) + "\src\main\resources\org\sonar\plugins\resharper\rules-cs.xml"), $Null);
    "VB" = New-Object System.XMl.XmlTextWriter(((Convert-Path $PSScriptRoot) + "\src\main\resources\org\sonar\plugins\resharper\rules-vb.xml"), $Null);
    "ASPX" = New-Object System.XMl.XmlTextWriter(((Convert-Path $PSScriptRoot) + "\src\main\resources\org\sonar\plugins\resharper\rules-aspx.xml"), $Null);
    "CSS" = New-Object System.XMl.XmlTextWriter(((Convert-Path $PSScriptRoot) + "\src\main\resources\org\sonar\plugins\resharper\rules-css.xml"), $Null);
    "RESX" = New-Object System.XMl.XmlTextWriter(((Convert-Path $PSScriptRoot) + "\src\main\resources\org\sonar\plugins\resharper\rules-resx.xml"), $Null);
    "TS" = New-Object System.XMl.XmlTextWriter(((Convert-Path $PSScriptRoot) + "\src\main\resources\org\sonar\plugins\resharper\rules-ts.xml"), $Null);
    "XA" = New-Object System.XMl.XmlTextWriter(((Convert-Path $PSScriptRoot) + "\src\main\resources\org\sonar\plugins\resharper\rules-xa.xml"), $Null);
    "HTML" = New-Object System.XMl.XmlTextWriter(((Convert-Path $PSScriptRoot) + "\src\main\resources\org\sonar\plugins\resharper\rules-html.xml"), $Null);
    "NANT" = New-Object System.XMl.XmlTextWriter(((Convert-Path $PSScriptRoot) + "\src\main\resources\org\sonar\plugins\resharper\rules-nant.xml"), $Null);
    "MSBUILD" = New-Object System.XMl.XmlTextWriter(((Convert-Path $PSScriptRoot) + "\src\main\resources\org\sonar\plugins\resharper\rules-msbuild.xml"), $Null);
    "WEBCFG" = New-Object System.XMl.XmlTextWriter(((Convert-Path $PSScriptRoot) + "\src\main\resources\org\sonar\plugins\resharper\rules-webcfg.xml"), $Null);
    "WEBSERVICE" = New-Object System.XMl.XmlTextWriter(((Convert-Path $PSScriptRoot) + "\src\main\resources\org\sonar\plugins\resharper\rules-webservice.xml"), $Null);
    "RAZOR" = New-Object System.XMl.XmlTextWriter(((Convert-Path $PSScriptRoot) + "\src\main\resources\org\sonar\plugins\resharper\rules-razor.xml"), $Null);
}

$issueLanguageMap = ConvertFrom-Json ((Get-Content .\issueLanguageMapping.json) -join "`n")
$issueLanguageMap = $issueLanguageMap.psobject.properties

foreach($lang in $rulesMap.Keys)
{
    $rulesMap[$lang].Formatting = "Indented"
    $rulesMap[$lang].Indentation = 2
    $rulesMap[$lang].IndentChar = " "
    $rulesMap[$lang].WriteStartElement("rules")
}
try
{
    $count = 0
    foreach($issue in $exported.Report.IssueTypes.ChildNodes)
    {
        if($issue.Severity -eq "INVALID_SEVERITY")
        {
            Write-Warning ($issue.Name + " has INVALID_SEVERITY, so skipping it")
            continue
        }
        $issueMapping = $issueLanguageMap[$issue.Id].Value
        if($issueMapping -eq $Null)
        {
            Write-Warning ("Cannot find " + $issue.Id + " in issueLanguageMapping.json")
            continue
        }
        if($issueMapping.Length -eq 0)
        {
            Write-Warning ($issue.Id + " doesn't have any languages configured in issueLanguageMapping.json")
            $count += 1
            continue
        }
        $issueMapping = $issueMapping.Split(",")
        foreach($lang in $issueMapping)
        {
            if(!$issueMapping.Contains($lang))
            {
                continue
            }
            if(!$rulesMap.ContainsKey($lang))
            {
                Write-Warning ($issue.Id + " has an invalid language configured ($lang)")
                continue
            }
    
            $description = $issue.Description
            if([string]::IsNullOrEmpty($description))
            {
                $description = $issue.Category
            }
            $rulesMap[$lang].WriteStartElement("rule")
            $rulesMap[$lang].WriteAttributeString("key", $issue.Id)
    
            # priority
            $rulesMap[$lang].WriteStartElement("priority")
            $rulesMap[$lang].WriteRaw($priorityMap[$issue.Severity])
            $rulesMap[$lang].WriteEndElement()

            # name
            $rulesMap[$lang].WriteStartElement("name")
            $rulesMap[$lang].WriteRaw("<![CDATA[" + $description + "]]>")
            $rulesMap[$lang].WriteEndElement()

            # description
            $rulesMap[$lang].WriteStartElement("description")
            if($issue.WikiUrl -ne $Null)
            {
                $rulesMap[$lang].WriteRaw("<![CDATA[<h2>JetBrains Code Inspection Wiki</h2> <p><a href=`"" + $issue.WikiUrl + "`">See`n      JetBrains documentation.</a></p>]]>")
            }
            else
            {
                $rulesMap[$lang].WriteRaw("<![CDATA[" + $description + "]]>")
            }
            $rulesMap[$lang].WriteEndElement()

            $rulesMap[$lang].WriteEndElement()
        }
    }
    $rulesMap[$lang].WriteEndElement()
}
finally
{
    Write-Host ("" + $count + " issues detected")
    foreach($lang in $rulesMap.Keys)
    {
        $rulesMap[$lang].Flush()
        $rulesMap[$lang].Close()
    }
}