package com.job.app.review.impl;

import com.job.app.company.Company;
import com.job.app.company.CompanyService;
import com.job.app.review.Review;
import com.job.app.review.ReviewRepository;
import com.job.app.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private ReviewRepository reviewRepository;
    private CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
        //return List.of();
    }

    @Override
    public boolean addReview(Long companyId, Review review) {

        Company companyOptional = companyService.getCompanyById(companyId);

        if (companyOptional !=null) {
            review.setCompany(companyOptional);  // ✅ pass Company, not Optional
            reviewRepository.save(review);
            return true;
        }

        return false;
    }

    @Override
    public Review getReview(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews.stream()
                .filter(review -> review.getId().equals(reviewId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean updateReview(Long companyId, Long reviewId, Review updatedReview) {
        if(reviewRepository.findByCompanyId(companyId)!=null){
            updatedReview.setCompany(companyService.getCompanyById(companyId));
            updatedReview.setId(reviewId);
            reviewRepository.save(updatedReview);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReview(Long companyId, Long reviewId) {
        if(companyService.getCompanyById(companyId)!=null
                && reviewRepository.existsById(reviewId)){
            Review review = reviewRepository.findById(reviewId).orElse(null);
            Company company = review.getCompany();
            company.getReview().remove(review);
            review.setCompany(null);
            companyService.updateCompanyById( companyId, company);
            reviewRepository.deleteById(reviewId);
            return true;
        }
        return false;
    }


}
