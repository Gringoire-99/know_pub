# USE know_pub;
# UPDATE post
# SET post.comment_count = 0;
#
# DELETE From comment
# WHERE 1;
UPDATE post SET like_count = like_count - 1 WHERE (id = '00a05c28-88d6-13e4-f2f7-5269b9734bd8')
