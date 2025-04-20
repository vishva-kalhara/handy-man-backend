1. Task Filtering
2. Getting bids in taskDTO
3. * test UserDTOAvgRating

# Notifications

[//]: # (- User creates account - To update profile picture and bio.)
[//]: # (- Handy Man bids for a task - Task owner gets notified)
[//]: # (- Task owner rejects a bid - Handy Man gets notified)
[//]: # (- Task owner accepts a bid - Chosen handy and other rejected bidders get notified)
- Task onwer accepts an offer - Task owner should get a message to complete the task
- Task owner completed a task - Both Handyman and Task owner get notified to review
- SHOULD update the has noted state to false after reviewing the other

# Tech Stack
- HQL
- JPA
- Hibernate
- Mapstruct
- Repository Pattern
- Spring Security
- AWS S3