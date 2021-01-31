package ise308.polat.utku.findjob

class JobData {

    var jobHeader : String? = null
    var jobInfo : String? = null
    var contactInfo : String? = null
    var jobSituation : Int? = null   // Since SQLite holds boolean values as 1 and 0, I declared that integer variable as Flag.

    constructor(jobHeader : String, jobInfo : String, contactInfo : String, jobSituation : Int) {
        this.jobHeader = jobHeader
        this.jobInfo = jobInfo
        this.contactInfo = contactInfo
        this.jobSituation = jobSituation
    }
    constructor(){}
}