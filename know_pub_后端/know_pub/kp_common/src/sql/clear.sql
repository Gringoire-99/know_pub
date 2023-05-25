USE know_pub;
UPDATE post
SET post.comment_count = 0;

DELETE From comment
WHERE 1;