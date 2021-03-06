package main.java;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import com.google.gson.annotations.SerializedName;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;

public class Issue {
    @SerializedName("number")
    String number;
    @SerializedName("comments_url")
    String comments_url;
    @SerializedName("user")
    User user;
    @SerializedName("pull_request")
    PullRequest pullRequest;
    @SerializedName("labels")
    IssuesLabel[] labels;
    @SerializedName("state")
    String state;
    @SerializedName("created_at")
    String created_at;
    @SerializedName("updated_at")
    String updated_at;
    @SerializedName("closed_at")
    String closed_at;
    @SerializedName("title")
    String title;
    @SerializedName("body")
    String body;
    private Date updated_at_date;
    private Date created_at_date;
    private Date closed_at_date;
    private boolean isValidIssue = true;

    int daysBetween;
    long minutesBetween;

    Issue(String created_at, String closed_at) {
        this.created_at = created_at;
        this.closed_at = closed_at;
        this.isValidIssue= true;
    }


    boolean isValidIssue() {
        return isValidIssue;
    }

    void setValidIssue(boolean validIssue) {
        isValidIssue = validIssue;
    }

	public IssuesLabel[] getLabels() {
		return labels;
	}

	public void setLabels(IssuesLabel[] labels) {
		this.labels = labels;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getUpdated_at_date() {
		return updated_at_date;
	}

	public Date getCreated_at_date() {
		return created_at_date;
	}

	public Date getClosed_at_date() {
		return closed_at_date;
	}

	

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public String getClosed_at() {
		return closed_at;
	}

	public void setClosed_at(String closed_at) {
		this.closed_at = closed_at;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	void calculateTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            this.created_at_date = format.parse(this.created_at);
            this.closed_at_date = format.parse(this.closed_at);

        } catch (ParseException e) {
            this.isValidIssue = false;
            e.printStackTrace();
        }
        //Be careful with UTC and Local time; For UTC it mightn't have happened
        // Also I cast to int, i don't think it takes more than 2147483647 days to close an issue
        this.daysBetween = (int) DAYS.between(this.created_at_date
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate(),
                this.closed_at_date
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());

        this.minutesBetween = MINUTES.between(this.created_at_date
                        .toInstant(),
                this.closed_at_date
                        .toInstant());
    }
}
