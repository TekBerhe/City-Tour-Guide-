#!/bin/bash
export PGPASSWORD='postgres1'
pg_dump -h localhost -p 5432 -U postgres --table="landmarks_photos" --data-only --column-inserts final_capstone > dumps/landmarks_photos.sql
pg_dump -h localhost -p 5432 -U postgres --table="address" --data-only --column-inserts final_capstone > dumps/address.sql
pg_dump -h localhost -p 5432 -U postgres --table="districts" --data-only --column-inserts final_capstone > dumps/districts.sql
pg_dump -h localhost -p 5432 -U postgres --table="itineraries" --data-only --column-inserts final_capstone > dumps/itineraries.sql
pg_dump -h localhost -p 5432 -U postgres --table="landmarks" --data-only --column-inserts final_capstone > dumps/landmarks.sql
pg_dump -h localhost -p 5432 -U postgres --table="landmarks_itinerary" --data-only --column-inserts final_capstone > dumps/landmarks_itinerary.sql
pg_dump -h localhost -p 5432 -U postgres --table="photos" --data-only --column-inserts final_capstone > dumps/photos.sql
pg_dump -h localhost -p 5432 -U postgres --table="reviews" --data-only --column-inserts final_capstone > dumps/reviews.sql
pg_dump -h localhost -p 5432 -U postgres --table="users" --data-only --column-inserts final_capstone > dumps/users.sql