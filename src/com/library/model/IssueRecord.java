package com.library.model;

import java.sql.Date;

public class IssueRecord {
    private int issueId;
    private int bookId;
    private int userId;
    private Date issueDate;
    private Date dueDate;
    private String status;

    public IssueRecord() {}

    public int getIssueId() { return issueId; }
    public void setIssueId(int issueId) { this.issueId = issueId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Date getIssueDate() { return issueDate; }
    public void setIssueDate(Date issueDate) { this.issueDate = issueDate; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "IssueRecord{id=" + issueId + ", book=" + bookId + ", user=" + userId +
                ", status='" + status + "'}";
    }
}